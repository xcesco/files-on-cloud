import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AbstractUserDetailComponent} from '../../user-detail.abstract';
import {Uploader} from '../../../types/users';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {UploaderService} from '../../../services/uploader.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {isBlank, isNotBlank} from '../../../shared/utils/utils';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-uploader-detail',
  templateUrl: './uploader-detail.component.html',
  styleUrls: ['./uploader-detail.component.scss']
})
export class UploaderDetailComponent extends AbstractUserDetailComponent<Uploader, UploaderService> implements OnInit {
  private form: FormGroup;

  labelUpload: ElementRef;
  private fileToUpload: File;
  private timeStamp = (new Date()).getTime();

  constructor(authService: AuthService, actr: ActivatedRoute, router: Router, service: UploaderService, location: Location, toastr: ToastrService) {
    super(authService, actr, router, service, location, toastr);

    this.form = new FormGroup({
      importFile: new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {
  }

  @ViewChild('labelImport', {static: false})
  set labelImport(v: ElementRef) {
    setTimeout(() => {
      this.labelUpload = v;
    }, 0);
  }

  get logoUrl(): string {
    const id = isNotBlank(this.user.id) ? this.user.id : 0;
    return `/api/v1/public/uploaders/${id}/logo?t=` + this.timeStamp;
  }

  uploadLogo() {
    console.log('avvio', this.fileToUpload);

    this.service.uploadLogo(this.user.id, this.fileToUpload).subscribe(() => {
        console.log('uploaded!!!');
        this.timeStamp = (new Date()).getTime();
      },
      (err) => console.log(err));
  }

  get disableUpload(): boolean {
    return isBlank(this.user.id) || isBlank(this.fileToUpload) || this.fileToUpload.size > 1_000_000;
  }

  onFileChange(files: FileList) {
    this.labelUpload.nativeElement.innerText = files.item(0).name;
    this.fileToUpload = files.item(0);
  }

}
