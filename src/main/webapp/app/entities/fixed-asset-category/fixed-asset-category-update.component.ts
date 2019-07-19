import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFixedAssetCategory, FixedAssetCategory } from 'app/shared/model/fixed-asset-category.model';
import { FixedAssetCategoryService } from './fixed-asset-category.service';

@Component({
  selector: 'gha-fixed-asset-category-update',
  templateUrl: './fixed-asset-category-update.component.html'
})
export class FixedAssetCategoryUpdateComponent implements OnInit {
  fixedAssetCategory: IFixedAssetCategory;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    categoryCode: [null, [Validators.required]],
    categoryName: [null, [Validators.required]],
    categoryDescription: [],
    depreciationRegimeId: []
  });

  constructor(
    protected fixedAssetCategoryService: FixedAssetCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fixedAssetCategory }) => {
      this.updateForm(fixedAssetCategory);
      this.fixedAssetCategory = fixedAssetCategory;
    });
  }

  updateForm(fixedAssetCategory: IFixedAssetCategory) {
    this.editForm.patchValue({
      id: fixedAssetCategory.id,
      categoryCode: fixedAssetCategory.categoryCode,
      categoryName: fixedAssetCategory.categoryName,
      categoryDescription: fixedAssetCategory.categoryDescription,
      depreciationRegimeId: fixedAssetCategory.depreciationRegimeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fixedAssetCategory = this.createFromForm();
    if (fixedAssetCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.fixedAssetCategoryService.update(fixedAssetCategory));
    } else {
      this.subscribeToSaveResponse(this.fixedAssetCategoryService.create(fixedAssetCategory));
    }
  }

  private createFromForm(): IFixedAssetCategory {
    const entity = {
      ...new FixedAssetCategory(),
      id: this.editForm.get(['id']).value,
      categoryCode: this.editForm.get(['categoryCode']).value,
      categoryName: this.editForm.get(['categoryName']).value,
      categoryDescription: this.editForm.get(['categoryDescription']).value,
      depreciationRegimeId: this.editForm.get(['depreciationRegimeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFixedAssetCategory>>) {
    result.subscribe((res: HttpResponse<IFixedAssetCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
