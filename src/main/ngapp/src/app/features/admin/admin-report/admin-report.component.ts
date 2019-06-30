import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ChangePasswordDialogService} from '../../../shared/components/change-password-dialog/change-password-dialog.service';
import {AdminService} from '../../../services/admin.service';
import {ToastrService} from 'ngx-toastr';
import {DetailedSummary} from '../../../types/users';
import {map} from 'rxjs/operators';
import {NgbCalendar, NgbDate, NgbDateAdapter, NgbDateNativeAdapter} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-admin-report',
  templateUrl: './admin-report.component.html',
  styleUrls: ['./admin-report.component.scss'],
  providers: [{provide: NgbDateAdapter, useClass: NgbDateNativeAdapter}]
})
export class AdminReportComponent implements OnInit {
  list: DetailedSummary[] = null;


  hoveredDate: NgbDate;

  fromDate: NgbDate;
  toDate: NgbDate;

  constructor(private calendar: NgbCalendar, private actr: ActivatedRoute, private confirmationDialogService: ConfirmationDialogService,
              private changePasswordDialogService: ChangePasswordDialogService,
              private adminService: AdminService, private toastr: ToastrService) {
    this.actr.data.pipe(map(data => data.list)).subscribe((value: DetailedSummary[]) => {
      // console.log('caricato', value);
      this.list = value;
    });
  }

  ngOnInit() {
    console.log('componentns --');
  }

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date.after(this.fromDate)) {
      this.toDate = date;
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
  }

  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate);
  }

  isInside(date: NgbDate) {
    return date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || date.equals(this.toDate) || this.isInside(date) || this.isHovered(date);
  }

  report() {
    this.adminService.getSummary(new Date(this.fromDate.year, this.fromDate.month, this.fromDate.day),
      new Date(this.toDate.year, this.toDate.month, this.toDate.day)).subscribe(result => {
      this.list = result;
    });
  }
}
