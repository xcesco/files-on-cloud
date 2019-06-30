import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ToastrService} from 'ngx-toastr';
import {CloudFileService} from '../../../services/cloud-file.service';
import {map} from 'rxjs/operators';

import {CloudFile, CloudFileTag} from '../../../types/files';
import {isNotBlank} from '../../../shared/utils/utils';
import {AuthService} from '../../../services/auth.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-cloud-file-table',
  templateUrl: './cloud-file-table.component.html',
  styleUrls: ['./cloud-file-table.component.scss']
})
export class CloudFileTableComponent implements OnInit {

  list: CloudFile[] = null;
  tags: CloudFileTag[] = null;
  filterTags: string[] = [];

  uploaderId: any;
  consumerId: any;

  constructor(private actr: ActivatedRoute,
              private confirmationDialogService: ConfirmationDialogService,
              private location: Location,
              private authService: AuthService,
              private service: CloudFileService,
              private toastr: ToastrService) {
    this.actr.queryParams.subscribe(params => {
      console.log('sss', params);
      this.uploaderId = params.uploaderId;
      this.consumerId = params.consumerId;
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
    const index: number = this.filterTags.indexOf(msg);
    if (index !== -1) {
      this.filterTags.splice(index, 1);
    } else {
      this.filterTags.push(msg);
    }
  }

  filterAdd(msg: string) {
    this.filterTags.push(msg);
  }

  filterRemove(msg: string) {
    const index: number = this.filterTags.indexOf(msg);
    if (index !== -1) {
      this.filterTags.splice(index, 1);
    }
  }

  filterContaings(msg: string) {
    return this.filterTags.indexOf(msg) !== -1;
  }


  ngOnInit() {
    console.log('componentns --');
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
    if (this.authService.hasRoleConsumer()) {
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
        this.service.delete(item).subscribe(() => this.service.findAll().subscribe((newList: CloudFile[]) => {
          this.list = newList;
          console.log('User reloaded', newList);
        }));

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
}
