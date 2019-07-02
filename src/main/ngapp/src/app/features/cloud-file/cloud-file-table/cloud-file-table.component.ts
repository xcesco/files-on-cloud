import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ToastrService} from 'ngx-toastr';
import {CloudFileService} from '../../../services/cloud-file.service';
import {map} from 'rxjs/operators';

import {CloudFile, CloudFileTag} from '../../../types/files';
import {isNotBlank} from '../../../shared/utils/utils';
import {AuthService} from '../../../services/auth.service';
import {Observable} from 'rxjs';
import {ConsumerService} from '../../../services/consumer.service';

@Component({
  selector: 'app-cloud-file-table',
  templateUrl: './cloud-file-table.component.html',
  styleUrls: ['./cloud-file-table.component.scss']
})
export class CloudFileTableComponent implements OnInit {

  list: CloudFile[] = null;
  tags: CloudFileTag[] = null;
  filterTags: string[] = [];

  uploaderId: number;
  consumerId: number;
  uploaderDisplayName: string;
  consumerDisplayName: string;
  allowGoBack: boolean;

  constructor(
    private consumerService: ConsumerService,
    private router: Router,
    private actr: ActivatedRoute,
    private confirmationDialogService: ConfirmationDialogService,
    private location: Location,
    private authService: AuthService,
    private service: CloudFileService,
    private toastr: ToastrService) {
    this.actr.queryParams.subscribe(params => {
      console.log('sss', params);
      this.uploaderId = params.uploaderId;
      this.uploaderDisplayName = params.uploaderDisplayName;
      this.consumerId = params.consumerId;
      this.consumerDisplayName = params.consumerDisplayName;

      this.allowGoBack = 'true' === params.allowGoBack;
    });


    this.actr.data.pipe(map(data => data.list)).subscribe((value: CloudFile[]) => {
      console.log('caricato', value);
      this.list = value;
    });

    if (this.authService.hasRoleConsumer()) {
      this.service.findAllTags(this.uploaderId, this.consumerId).subscribe(items => {
        this.tags = items;
      });
    }

  }

  filterInvert(msg: string) {
    console.log('seleziono tag', msg);
    const index: number = this.filterTags.indexOf(msg);
    if (index !== -1) {
      this.filterTags.splice(index, 1);
    } else {
      this.filterTags.push(msg);
    }
  }

  filterContaings(msg: string) {
    return this.filterTags.indexOf(msg) !== -1;
  }

  ngOnInit() {
    console.log('componentns --');

    // se e' admin, non ha bisogno di tornare indietro
    if (this.authService.hasRoleAdministrator()) {
      this.allowGoBack = false;
    }
  }

  getLogoUrl(): string {
    return `/api/v1/public/uploaders/${this.uploaderId}/logo`;
  }

  isViewed(item: CloudFile): boolean {
    return isNotBlank(item.viewIp);
  }

  refresh(): void {
    if (this.authService.hasRoleConsumer()) {
      this.service.findAllTags(this.uploaderId, this.consumerId).subscribe(items => {
        this.tags = items;
      });
    }

    this.load().subscribe(value => {
      this.list = value;
    });
  }

  private load(): Observable<CloudFile[]> {
    console.log('reload');
    if (this.authService.hasRoleConsumer() || this.authService.hasRoleUploader()) {
      // se consumer, deve per forza avere uploader e consumer (
      return this.service.findByUploaderAndConsumer(this.uploaderId, this.consumerId);
    } else {
      return this.service.findAll();
    }
  }

  onDelete(item: CloudFile) {
    this.confirmationDialogService.confirm('Please confirm..', `Do you really want to delete file ${item.fileName}?`)
      .then((confirmed) => {
        console.log('User confirmed:', confirmed);
        this.toastr.info(`User was correctly deleted!`, 'User deletion');
        this.service.delete(item).subscribe(() => this.refresh());
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

  onSendNotification(item: CloudFile) {
    this.confirmationDialogService.confirm('Please confirm..', `Do you really want to send notification again for  ${item.fileName}?`)
      .then((confirmed) => {

        this.service.sendNotification(item).subscribe(() => this.service.findAll().subscribe((newList: CloudFile[]) => {
          this.toastr.success(`Notifcation sent, again`, 'Notification status');
        }));

      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

  goBack(): void {
    console.log('go back');
    this.location.back();
  }

  onDownload(uuid: string) {
    setTimeout(() => {
      this.refresh();
    }, 3000);
  }

  filter() {
    this.service.findByUploaderAndConsumerWithTags(this.uploaderId, this.consumerId, this.filterTags).subscribe(value => {
      this.list = value;
    });

  }

  gotoNew() {
    this.consumerService.get(this.consumerId).subscribe(consumer => {
      this.router.navigate(['files', 'create'], {
        queryParams: {
          consumerCodiceFiscale: consumer.codiceFiscale
        }
      });
    });

  }


}
