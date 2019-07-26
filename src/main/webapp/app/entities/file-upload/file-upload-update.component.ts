import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFileUpload, FileUpload } from 'app/shared/model/file-upload.model';
import { FileUploadService } from './file-upload.service';

@Component({
  selector: 'gha-file-upload-update',
  templateUrl: './file-upload-update.component.html'
})
export class FileUploadUpdateComponent implements OnInit {
  fileUpload: IFileUpload;
  isSaving: boolean;
  periodFromDp: any;
  periodToDp: any;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    fileName: [null, [Validators.required]],
    periodFrom: [],
    periodTo: [],
    fileTypeId: [null, [Validators.required]],
    dataFile: [null, [Validators.required]],
    dataFileContentType: [],
    uploadSuccessful: [],
    uploadProcessed: [],
    uploadToken: [null, []]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected fileUploadService: FileUploadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fileUpload }) => {
      this.updateForm(fileUpload);
      this.fileUpload = fileUpload;
    });
  }

  updateForm(fileUpload: IFileUpload) {
    this.editForm.patchValue({
      id: fileUpload.id,
      description: fileUpload.description,
      fileName: fileUpload.fileName,
      periodFrom: fileUpload.periodFrom,
      periodTo: fileUpload.periodTo,
      fileTypeId: fileUpload.fileTypeId,
      dataFile: fileUpload.dataFile,
      dataFileContentType: fileUpload.dataFileContentType,
      uploadSuccessful: fileUpload.uploadSuccessful,
      uploadProcessed: fileUpload.uploadProcessed,
      uploadToken: fileUpload.uploadToken
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fileUpload = this.createFromForm();
    if (fileUpload.id !== undefined) {
      this.subscribeToSaveResponse(this.fileUploadService.update(fileUpload));
    } else {
      this.subscribeToSaveResponse(this.fileUploadService.create(fileUpload));
    }
  }

  private createFromForm(): IFileUpload {
    const entity = {
      ...new FileUpload(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      fileName: this.editForm.get(['fileName']).value,
      periodFrom: this.editForm.get(['periodFrom']).value,
      periodTo: this.editForm.get(['periodTo']).value,
      fileTypeId: this.editForm.get(['fileTypeId']).value,
      dataFileContentType: this.editForm.get(['dataFileContentType']).value,
      dataFile: this.editForm.get(['dataFile']).value,
      uploadSuccessful: this.editForm.get(['uploadSuccessful']).value,
      uploadProcessed: this.editForm.get(['uploadProcessed']).value,
      uploadToken: this.editForm.get(['uploadToken']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileUpload>>) {
    result.subscribe((res: HttpResponse<IFileUpload>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
