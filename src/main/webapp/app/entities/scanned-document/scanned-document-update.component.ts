import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IScannedDocument, ScannedDocument } from 'app/shared/model/scanned-document.model';
import { ScannedDocumentService } from './scanned-document.service';

@Component({
  selector: 'jhi-scanned-document-update',
  templateUrl: './scanned-document-update.component.html'
})
export class ScannedDocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    description: [],
    approvalDocument: [],
    approvalDocumentContentType: [],
    invoiceDocument: [],
    invoiceDocumentContentType: [],
    lpoDocument: [],
    lpoDocumentContentType: [],
    requisitionDocument: [],
    requisitionDocumentContentType: [],
    otherDocuments: [],
    otherDocumentsContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected scannedDocumentService: ScannedDocumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ scannedDocument }) => {
      this.updateForm(scannedDocument);
    });
  }

  updateForm(scannedDocument: IScannedDocument) {
    this.editForm.patchValue({
      id: scannedDocument.id,
      description: scannedDocument.description,
      approvalDocument: scannedDocument.approvalDocument,
      approvalDocumentContentType: scannedDocument.approvalDocumentContentType,
      invoiceDocument: scannedDocument.invoiceDocument,
      invoiceDocumentContentType: scannedDocument.invoiceDocumentContentType,
      lpoDocument: scannedDocument.lpoDocument,
      lpoDocumentContentType: scannedDocument.lpoDocumentContentType,
      requisitionDocument: scannedDocument.requisitionDocument,
      requisitionDocumentContentType: scannedDocument.requisitionDocumentContentType,
      otherDocuments: scannedDocument.otherDocuments,
      otherDocumentsContentType: scannedDocument.otherDocumentsContentType
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
    const scannedDocument = this.createFromForm();
    if (scannedDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.scannedDocumentService.update(scannedDocument));
    } else {
      this.subscribeToSaveResponse(this.scannedDocumentService.create(scannedDocument));
    }
  }

  private createFromForm(): IScannedDocument {
    return {
      ...new ScannedDocument(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      approvalDocumentContentType: this.editForm.get(['approvalDocumentContentType']).value,
      approvalDocument: this.editForm.get(['approvalDocument']).value,
      invoiceDocumentContentType: this.editForm.get(['invoiceDocumentContentType']).value,
      invoiceDocument: this.editForm.get(['invoiceDocument']).value,
      lpoDocumentContentType: this.editForm.get(['lpoDocumentContentType']).value,
      lpoDocument: this.editForm.get(['lpoDocument']).value,
      requisitionDocumentContentType: this.editForm.get(['requisitionDocumentContentType']).value,
      requisitionDocument: this.editForm.get(['requisitionDocument']).value,
      otherDocumentsContentType: this.editForm.get(['otherDocumentsContentType']).value,
      otherDocuments: this.editForm.get(['otherDocuments']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScannedDocument>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
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
