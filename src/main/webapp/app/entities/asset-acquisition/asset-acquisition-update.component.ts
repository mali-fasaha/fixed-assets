import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IAssetAcquisition, AssetAcquisition } from 'app/shared/model/asset-acquisition.model';
import { AssetAcquisitionService } from './asset-acquisition.service';

@Component({
  selector: 'gha-asset-acquisition-update',
  templateUrl: './asset-acquisition-update.component.html'
})
export class AssetAcquisitionUpdateComponent implements OnInit {
  assetAcquisition: IAssetAcquisition;
  isSaving: boolean;
  acquisitionMonthDp: any;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    acquisitionMonth: [null, [Validators.required]],
    assetSerial: [null, [Validators.required]],
    serviceOutletCode: [null, [Validators.required]],
    acquisitionTransactionId: [null, [Validators.required]],
    assetCategoryId: [null, [Validators.required]],
    purchaseAmount: [null, [Validators.required]],
    assetDealerId: [],
    assetInvoiceId: []
  });

  constructor(
    protected assetAcquisitionService: AssetAcquisitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ assetAcquisition }) => {
      this.updateForm(assetAcquisition);
      this.assetAcquisition = assetAcquisition;
    });
  }

  updateForm(assetAcquisition: IAssetAcquisition) {
    this.editForm.patchValue({
      id: assetAcquisition.id,
      description: assetAcquisition.description,
      acquisitionMonth: assetAcquisition.acquisitionMonth,
      assetSerial: assetAcquisition.assetSerial,
      serviceOutletCode: assetAcquisition.serviceOutletCode,
      acquisitionTransactionId: assetAcquisition.acquisitionTransactionId,
      assetCategoryId: assetAcquisition.assetCategoryId,
      purchaseAmount: assetAcquisition.purchaseAmount,
      assetDealerId: assetAcquisition.assetDealerId,
      assetInvoiceId: assetAcquisition.assetInvoiceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const assetAcquisition = this.createFromForm();
    if (assetAcquisition.id !== undefined) {
      this.subscribeToSaveResponse(this.assetAcquisitionService.update(assetAcquisition));
    } else {
      this.subscribeToSaveResponse(this.assetAcquisitionService.create(assetAcquisition));
    }
  }

  private createFromForm(): IAssetAcquisition {
    const entity = {
      ...new AssetAcquisition(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      acquisitionMonth: this.editForm.get(['acquisitionMonth']).value,
      assetSerial: this.editForm.get(['assetSerial']).value,
      serviceOutletCode: this.editForm.get(['serviceOutletCode']).value,
      acquisitionTransactionId: this.editForm.get(['acquisitionTransactionId']).value,
      assetCategoryId: this.editForm.get(['assetCategoryId']).value,
      purchaseAmount: this.editForm.get(['purchaseAmount']).value,
      assetDealerId: this.editForm.get(['assetDealerId']).value,
      assetInvoiceId: this.editForm.get(['assetInvoiceId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssetAcquisition>>) {
    result.subscribe((res: HttpResponse<IAssetAcquisition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
