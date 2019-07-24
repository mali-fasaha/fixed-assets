import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IAssetDepreciation, AssetDepreciation } from 'app/shared/model/asset-depreciation.model';
import { AssetDepreciationService } from './asset-depreciation.service';

@Component({
  selector: 'gha-asset-depreciation-update',
  templateUrl: './asset-depreciation-update.component.html'
})
export class AssetDepreciationUpdateComponent implements OnInit {
  assetDepreciation: IAssetDepreciation;
  isSaving: boolean;
  depreciationDateDp: any;

  editForm = this.fb.group({
    id: [],
    description: [],
    depreciationAmount: [null, [Validators.required]],
    depreciationDate: [null, [Validators.required]],
    categoryId: [],
    assetItemId: []
  });

  constructor(
    protected assetDepreciationService: AssetDepreciationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ assetDepreciation }) => {
      this.updateForm(assetDepreciation);
      this.assetDepreciation = assetDepreciation;
    });
  }

  updateForm(assetDepreciation: IAssetDepreciation) {
    this.editForm.patchValue({
      id: assetDepreciation.id,
      description: assetDepreciation.description,
      depreciationAmount: assetDepreciation.depreciationAmount,
      depreciationDate: assetDepreciation.depreciationDate,
      categoryId: assetDepreciation.categoryId,
      assetItemId: assetDepreciation.assetItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const assetDepreciation = this.createFromForm();
    if (assetDepreciation.id !== undefined) {
      this.subscribeToSaveResponse(this.assetDepreciationService.update(assetDepreciation));
    } else {
      this.subscribeToSaveResponse(this.assetDepreciationService.create(assetDepreciation));
    }
  }

  private createFromForm(): IAssetDepreciation {
    const entity = {
      ...new AssetDepreciation(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      depreciationAmount: this.editForm.get(['depreciationAmount']).value,
      depreciationDate: this.editForm.get(['depreciationDate']).value,
      categoryId: this.editForm.get(['categoryId']).value,
      assetItemId: this.editForm.get(['assetItemId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssetDepreciation>>) {
    result.subscribe((res: HttpResponse<IAssetDepreciation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
