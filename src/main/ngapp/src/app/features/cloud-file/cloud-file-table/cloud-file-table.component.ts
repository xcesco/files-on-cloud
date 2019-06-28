import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ToastrService} from 'ngx-toastr';
import {CloudFileService} from '../../../services/cloud-file.service';
import {map} from 'rxjs/operators';
import {CloudFile} from '../../../types/files';
import {isNotBlank} from '../../../shared/utils/utils';

@Component({
  selector: 'app-cloud-file-table',
  templateUrl: './cloud-file-table.component.html',
  styleUrls: ['./cloud-file-table.component.scss']
})
export class CloudFileTableComponent implements OnInit {

  list: CloudFile[] = null;

  constructor(private actr: ActivatedRoute,
              private confirmationDialogService: ConfirmationDialogService,
              private service: CloudFileService,
              private toastr: ToastrService) {
    this.actr.data.pipe(map(data => data.list)).subscribe((value: CloudFile[]) => {
      console.log('caricato', value);
      this.list = value;
    });

  }

  ngOnInit() {
    console.log('componentns --');
  }

  isViewed(item: CloudFile): boolean {
    return isNotBlank(item.viewIp);
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
}
