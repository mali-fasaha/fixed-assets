import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ICwipTransfer, CwipTransfer } from 'app/shared/model/cwip-transfer.model';
import { CwipTransferService } from './cwip-transfer.service';

@Component({
  selector: 'gha-cwip-transfer-update',
  templateUrl: './cwip-transfer-update.component.html'
})
export class CwipTransferUpdateComponent implements OnInit {
  cwipTransfer: ICwipTransfer;
  isSaving: boolean;
  transferMonthDp: any;

  editForm = this.fb.group({
    id: [],
    transferMonth: [null, [Validators.required]],
    assetSerialTag: [null, [Validators.required]],
    serviceOutletCode: [null, [Validators.required]],
    transferTransactionId: [null, [Validators.required]],
    assetCategoryId: [null, [Validators.required]],
    cwipTransactionId: [null, [Validators.required]],
    transferDetails: [null, [Validators.required]],
    transferAmount: [null, [Validators.required]],
    dealerId: [],
    transactionInvoiceId: []
  });

  constructor(protected cwipTransferService: CwipTransferService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cwipTransfer }) => {
      this.updateForm(cwipTransfer);
      this.cwipTransfer = cwipTransfer;
    });
  }

  updateForm(cwipTransfer: ICwipTransfer) {
    this.editForm.patchValue({
      id: cwipTransfer.id,
      transferMonth: cwipTransfer.transferMonth,
      assetSerialTag: cwipTransfer.assetSerialTag,
      serviceOutletCode: cwipTransfer.serviceOutletCode,
      transferTransactionId: cwipTransfer.transferTransactionId,
      assetCategoryId: cwipTransfer.assetCategoryId,
      cwipTransactionId: cwipTransfer.cwipTransactionId,
      transferDetails: cwipTransfer.transferDetails,
      transferAmount: cwipTransfer.transferAmount,
      dealerId: cwipTransfer.dealerId,
      transactionInvoiceId: cwipTransfer.transactionInvoiceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cwipTransfer = this.createFromForm();
    if (cwipTransfer.id !== undefined) {
      this.subscribeToSaveResponse(this.cwipTransferService.update(cwipTransfer));
    } else {
      this.subscribeToSaveResponse(this.cwipTransferService.create(cwipTransfer));
    }
  }

  private createFromForm(): ICwipTransfer {
    const entity = {
      ...new CwipTransfer(),
      id: this.editForm.get(['id']).value,
      transferMonth: this.editForm.get(['transferMonth']).value,
      assetSerialTag: this.editForm.get(['assetSerialTag']).value,
      serviceOutletCode: this.editForm.get(['serviceOutletCode']).value,
      transferTransactionId: this.editForm.get(['transferTransactionId']).value,
      assetCategoryId: this.editForm.get(['assetCategoryId']).value,
      cwipTransactionId: this.editForm.get(['cwipTransactionId']).value,
      transferDetails: this.editForm.get(['transferDetails']).value,
      transferAmount: this.editForm.get(['transferAmount']).value,
      dealerId: this.editForm.get(['dealerId']).value,
      transactionInvoiceId: this.editForm.get(['transactionInvoiceId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICwipTransfer>>) {
    result.subscribe((res: HttpResponse<ICwipTransfer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
