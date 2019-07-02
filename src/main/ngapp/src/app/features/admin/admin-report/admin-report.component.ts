import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ConfirmationDialogService} from '../../../shared/components/confirmation-dialog/confirmation-dialog.service';
import {ChangePasswordDialogService} from '../../../shared/components/change-password-dialog/change-password-dialog.service';
import {AdminService} from '../../../services/admin.service';
import {ToastrService} from 'ngx-toastr';
import {Administrator, AdminReportItem, Consumer, Uploader} from '../../../types/users';
import {map} from 'rxjs/operators';
import {NgbCalendar, NgbDate, NgbDateAdapter, NgbDateNativeAdapter} from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import {Moment} from 'moment';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {isNotBlank} from '../../../shared/utils/utils';

@Component({
  selector: 'app-admin-report',
  templateUrl: './admin-report.component.html',
  styleUrls: ['./admin-report.component.scss'],
  providers: [{provide: NgbDateAdapter, useClass: NgbDateNativeAdapter}]
})
export class AdminReportComponent implements OnInit {
  list: AdminReportItem[] = null;

  loading = false;

  form: FormGroup = null;

  /**
   * https://ng-bootstrap.github.io/#/components/datepicker/overview
   *
   */
  constructor(private calendar: NgbCalendar, private actr: ActivatedRoute, private confirmationDialogService: ConfirmationDialogService,
              private changePasswordDialogService: ChangePasswordDialogService,
              private adminService: AdminService, private toastr: ToastrService) {
    // dp.navigateTo({year: 2013, month: 2})
    this.createForm();

    this.loading = true;
    this.actr.data.pipe(map(data => data.list)).subscribe((value: AdminReportItem[]) => {
      // console.log('caricato', value);
      this.list = value;
      this.loading = false;
    });
  }

  createForm() {
    const oggi = moment(new Date());
    const inizioMesePrecendente = moment(new Date()).subtract(1, 'month').startOf('month');

    this.form = new FormGroup({
      dataDal: new FormControl(inizioMesePrecendente.toDate(), [Validators.required]),
      dataAl: new FormControl(oggi.toDate(), [Validators.required])
    });

    console.log('1. this.form.value', this.form.value);
  }

  ngOnInit() {

  }

  isValidForm(): boolean {
    if (this.form === null || !this.form.valid) {
      return false;
    }
    const validoDal = moment(this.form.get('dataDal').value);
    const validoAl = moment(this.form.get('dataAl').value);
    // console.log('test', validoDal, validoAl);
    return this.form.valid && (!validoDal.isAfter(validoAl));
  }


  onSave() {
    console.log(this.form.value);

    this.loading = true;
    this.adminService.getReport(
      this.form.get('dataDal').value,
      this.form.get('dataAl').value)
      .subscribe(result => {
        this.list = result;
        this.loading = false;
      });
  }
}
