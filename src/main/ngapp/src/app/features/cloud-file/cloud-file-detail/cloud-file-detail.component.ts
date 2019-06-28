import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {ToastrService} from 'ngx-toastr';
import {map} from 'rxjs/operators';
import {CloudFileService} from '../../../services/cloud-file.service';
import {CloudFile, CloudFileData} from '../../../types/files';
import {HttpErrorResponse} from '@angular/common/http';
import {Form, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-cloud-file-detail',
  templateUrl: './cloud-file-detail.component.html',
  styleUrls: ['./cloud-file-detail.component.scss']
})
export class CloudFileDetailComponent implements OnInit {

  form: FormGroup;
  detail: CloudFile;
  labelUpload: ElementRef;
  file: File;

  constructor(protected actr: ActivatedRoute, protected service: CloudFileService, protected location: Location, protected toastr: ToastrService) {
    this.form = new FormGroup({
      codiceFiscale: new FormControl(null, Validators.required),
      displayName: new FormControl(null),
      email: new FormControl(null, [Validators.email]),
      file: new FormControl(null, [Validators.required]),
      hashtag: new FormControl(null),
      uploaderId: new FormControl(null, Validators.required),
      username: new FormControl(null, [Validators.email])
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
    formData.append('hashtag', this.form.get('hashtag').value);
    formData.append('uploaderId', this.form.get('uploaderId').value);
    formData.append('username', this.form.get('username').value);

    console.log(this.form.value, formData);
    this.service.save(this.form.get('uploaderId').value, formData).subscribe(
      (res) => {
        console.log(res);
        this.toastr.info('File correctly uploaded and notified', 'Information');
        this.goBack();
      },
      (err) => {
        console.log(err);
        this.toastr.error('Something went wrong!', 'Information');
      }
    );
    /*this.service.save()
    this.httpClient.post<any>(this.SERVER_URL, formData).subscribe(
      (res) => console.log(res),
      (err) => console.log(err)
    );*/

    /*  this.service.save(user).subscribe(data => {
        console.log('datao salvoat');
        this.toastr.success(`User is updated!`, 'System information');
        this.location.back();
      }, (error: HttpErrorResponse) => {
        // this.toastr.success(`User was correctly updated!`, 'User save!');
        this.toastr.error('Something went wrong during save operation!', 'System information');
        console.log('errorere', error.error);
      });*/
  }

  onFileChange(files: FileList) {
    this.labelUpload.nativeElement.innerText = files[0].name;
    if (files.length > 0) {
      this.file = files[0];
      // this.form.get('file').setValue(file);
    }

  }
}
