import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDepreciationRegime, DepreciationRegime } from 'app/shared/model/depreciation-regime.model';
import { DepreciationRegimeService } from './depreciation-regime.service';

@Component({
  selector: 'gha-depreciation-regime-update',
  templateUrl: './depreciation-regime-update.component.html'
})
export class DepreciationRegimeUpdateComponent implements OnInit {
  depreciationRegime: IDepreciationRegime;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    assetDecayType: [null, [Validators.required]],
    depreciationRate: [null, [Validators.required]],
    description: []
  });

  constructor(
    protected depreciationRegimeService: DepreciationRegimeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ depreciationRegime }) => {
      this.updateForm(depreciationRegime);
      this.depreciationRegime = depreciationRegime;
    });
  }

  updateForm(depreciationRegime: IDepreciationRegime) {
    this.editForm.patchValue({
      id: depreciationRegime.id,
      assetDecayType: depreciationRegime.assetDecayType,
      depreciationRate: depreciationRegime.depreciationRate,
      description: depreciationRegime.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const depreciationRegime = this.createFromForm();
    if (depreciationRegime.id !== undefined) {
      this.subscribeToSaveResponse(this.depreciationRegimeService.update(depreciationRegime));
    } else {
      this.subscribeToSaveResponse(this.depreciationRegimeService.create(depreciationRegime));
    }
  }

  private createFromForm(): IDepreciationRegime {
    const entity = {
      ...new DepreciationRegime(),
      id: this.editForm.get(['id']).value,
      assetDecayType: this.editForm.get(['assetDecayType']).value,
      depreciationRate: this.editForm.get(['depreciationRate']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepreciationRegime>>) {
    result.subscribe((res: HttpResponse<IDepreciationRegime>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
