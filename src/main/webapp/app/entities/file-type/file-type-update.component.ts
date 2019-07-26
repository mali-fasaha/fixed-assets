import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFileType, FileType } from 'app/shared/model/file-type.model';
import { FileTypeService } from './file-type.service';

@Component({
  selector: 'gha-file-type-update',
  templateUrl: './file-type-update.component.html'
})
export class FileTypeUpdateComponent implements OnInit {
  fileType: IFileType;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    fileTypeName: [null, [Validators.required]],
    fileMediumType: [null, [Validators.required]],
    description: []
  });

  constructor(protected fileTypeService: FileTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fileType }) => {
      this.updateForm(fileType);
      this.fileType = fileType;
    });
  }

  updateForm(fileType: IFileType) {
    this.editForm.patchValue({
      id: fileType.id,
      fileTypeName: fileType.fileTypeName,
      fileMediumType: fileType.fileMediumType,
      description: fileType.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fileType = this.createFromForm();
    if (fileType.id !== undefined) {
      this.subscribeToSaveResponse(this.fileTypeService.update(fileType));
    } else {
      this.subscribeToSaveResponse(this.fileTypeService.create(fileType));
    }
  }

  private createFromForm(): IFileType {
    const entity = {
      ...new FileType(),
      id: this.editForm.get(['id']).value,
      fileTypeName: this.editForm.get(['fileTypeName']).value,
      fileMediumType: this.editForm.get(['fileMediumType']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileType>>) {
    result.subscribe((res: HttpResponse<IFileType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
