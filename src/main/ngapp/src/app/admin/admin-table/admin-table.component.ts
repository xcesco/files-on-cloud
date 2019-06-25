import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Administrator, User} from '../../types/users';
import {map} from 'rxjs/operators';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ConfirmationDialogService} from '../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {AdminService} from '../../services/admin.service';


@Component({
  selector: 'app-admins-table',
  templateUrl: './admin-table.component.html',
  styleUrls: ['./admin-table.component.scss']
})
export class AdminTableComponent implements OnInit {

  list: Administrator[];

  constructor(private actr: ActivatedRoute, private confirmationDialogService: ConfirmationDialogService, private adminService: AdminService) {
    this.actr.data.pipe(map(data => data.list)).subscribe((value: Administrator[]) => {
      console.log('caricato', value);
      this.list = value;
    });
  }

  ngOnInit() {
    console.log('componentns --');
  }

  onDelete(user: User) {
    this.confirmationDialogService.confirm('Please confirm..', `Do you really want to delete user ${user.username}?`)
      .then((confirmed) => {
        console.log('User confirmed:', confirmed);
        this.adminService.delete(user).pipe(() => this.adminService.findAll()).subscribe(newList => {
          this.list = newList;
        });

      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

}
