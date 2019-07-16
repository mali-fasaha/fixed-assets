import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IAssetTransaction, AssetTransaction } from 'app/shared/model/asset-transaction.model';
import { AssetTransactionService } from './asset-transaction.service';

@Component({
  selector: 'gha-asset-transaction-update',
  templateUrl: './asset-transaction-update.component.html'
})
export class AssetTransactionUpdateComponent implements OnInit {
  isSaving: boolean;
  transactionDateDp: any;

  editForm = this.fb.group({
    id: [],
    transactionReference: [null, [Validators.required]],
    transactionDate: [null, [Validators.required]],
    scannedDocumentId: []
  });

  constructor(
    protected assetTransactionService: AssetTransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ assetTransaction }) => {
      this.updateForm(assetTransaction);
    });
  }

  updateForm(assetTransaction: IAssetTransaction) {
    this.editForm.patchValue({
      id: assetTransaction.id,
      transactionReference: assetTransaction.transactionReference,
      transactionDate: assetTransaction.transactionDate,
      scannedDocumentId: assetTransaction.scannedDocumentId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const assetTransaction = this.createFromForm();
    if (assetTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.assetTransactionService.update(assetTransaction));
    } else {
      this.subscribeToSaveResponse(this.assetTransactionService.create(assetTransaction));
    }
  }

  private createFromForm(): IAssetTransaction {
    return {
      ...new AssetTransaction(),
      id: this.editForm.get(['id']).value,
      transactionReference: this.editForm.get(['transactionReference']).value,
      transactionDate: this.editForm.get(['transactionDate']).value,
      scannedDocumentId: this.editForm.get(['scannedDocumentId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssetTransaction>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
