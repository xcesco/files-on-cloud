import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ChangePasswordDialogService} from '../../../shared/components/change-password-dialog/change-password-dialog.service';
import {AdminService} from '../../../services/admin.service';
import {ToastrService} from 'ngx-toastr';
import {AdminReportItem} from '../../../types/users';
import {map} from 'rxjs/operators';
import {NgbCalendar, NgbDate, NgbDateAdapter, NgbDateNativeAdapter} from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import {Moment} from 'moment';

@Component({
  selector: 'app-admin-report',
  templateUrl: './admin-report.component.html',
  styleUrls: ['./admin-report.component.scss'],
  providers: [{provide: NgbDateAdapter, useClass: NgbDateNativeAdapter}]
})
export class AdminReportComponent implements OnInit {
  list: AdminReportItem[] = null;

  hoveredDate: NgbDate = null;
  fromDate: NgbDate = null;
  toDate: NgbDate = null;

  start: Moment;
  end: Moment;

  /**
   * https://ng-bootstrap.github.io/#/components/datepicker/overview
   *
   * @param calendar
   * @param actr
   * @param confirmationDialogService
   * @param changePasswordDialogService
   * @param adminService
   * @param toastr
   */
  constructor(private calendar: NgbCalendar, private actr: ActivatedRoute, private confirmationDialogService: ConfirmationDialogService,
              private changePasswordDialogService: ChangePasswordDialogService,
              private adminService: AdminService, private toastr: ToastrService) {
    this.start = moment().subtract(1, 'month').startOf('month');
    this.end = moment().subtract(1, 'month').endOf('month');

    this.fromDate = NgbDate.from({year: this.start.year(), month: this.start.month() + 1, day: 1});
    this.toDate = NgbDate.from({year: this.end.year(), month: this.end.month() + 1, day: this.end.daysInMonth()});
    this.actr.data.pipe(map(data => data.list)).subscribe((value: AdminReportItem[]) => {
      // console.log('caricato', value);
      this.list = value;
    });
  }

  ngOnInit() {

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
    if (this.fromDate === null || this.toDate === null) {
      this.toastr.error('Please select a valid date range', 'Unable to run report');
    } else {
      this.adminService.getReport(new Date(this.fromDate.year, this.fromDate.month - 1, this.fromDate.day),
        new Date(this.toDate.year, this.toDate.month - 1, this.toDate.day)).subscribe(result => {
        this.list = result;
      });
    }
  }
}
