import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {map} from 'rxjs/operators';
import {CloudFileService} from '../../../services/cloud-file.service';
import {CloudFile, CloudFileData} from '../../../types/files';
import {HttpErrorResponse} from '@angular/common/http';
import {Form, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {isBlank, isNotBlank} from '../../../shared/utils/utils';
import {Consumer} from '../../../types/users';

@Component({
  selector: 'app-cloud-file-detail',
  templateUrl: './cloud-file-detail.component.html',
  styleUrls: ['./cloud-file-detail.component.scss']
})
export class CloudFileDetailComponent implements OnInit {

  form: FormGroup;
  detail: CloudFile;
  labelUpload: ElementRef;
  loading = false;
  file: File;
  private consumerCodiceFiscale: string;

  constructor(protected actr: ActivatedRoute, protected service: CloudFileService, protected location: Location, protected toastr: ToastrService) {
    this.actr.queryParams.subscribe(params => {
      this.consumerCodiceFiscale = params.consumerCodiceFiscale;

      this.form = new FormGroup({
        codiceFiscale: new FormControl(this.consumerCodiceFiscale, [Validators.required, Validators.minLength(16), Validators.maxLength(16)]),
        displayName: new FormControl(null, Validators.maxLength(64)),
        email: new FormControl(null, [Validators.email, Validators.maxLength(64)]),
        file: new FormControl(null, [Validators.required]),
        hashtag: new FormControl(null),
        username: new FormControl(null, [Validators.email, Validators.maxLength(64)])
      });
    });


  }

  ngOnInit() {

  }

  @ViewChild('labelImport', {static: false})
  set labelImport(v: ElementRef) {
    setTimeout(() => {
      this.labelUpload = v;
    }, 0);
  }

  goBack(): void {
    console.log('go back');
    this.location.back();
  }

  onSave() {
    console.log('faccio save');

    const formData = new FormData();
    formData.append('codiceFiscale', this.form.get('codiceFiscale').value);
    formData.append('displayName', this.form.get('displayName').value);
    formData.append('email', this.form.get('email').value);
    formData.append('file', this.file);
    if (isNotBlank(this.form.get('hashtag').value)) {
      formData.append('hashtag', this.form.get('hashtag').value);
    }
    formData.append('username', this.form.get('username').value);


    this.loading = true;
    console.log(this.form.value, formData);
    this.service.save(formData).subscribe(
      (res) => {
        console.log(res);
        this.toastr.info('File correctly uploaded and notified', 'Information');
        this.loading = false;
        this.goBack();
      },
      (err) => {
        console.log(err);
        this.toastr.error('Something went wrong!', 'Error');
        this.loading = false;
      }
    );
  }

  isFormInvalid(): boolean {
    return this.form.invalid || isBlank(this.file) || this.file.size > 1_000_000;
  }

  onFileChange(files: FileList) {
    this.labelUpload.nativeElement.innerText = files[0].name;
    if (files.length > 0) {
      this.file = files[0];
      // this.form.get('file').setValue(file);
    }

  }
}
