import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFixedAssetAssessment, FixedAssetAssessment } from 'app/shared/model/fixed-asset-assessment.model';
import { FixedAssetAssessmentService } from './fixed-asset-assessment.service';

@Component({
  selector: 'gha-fixed-asset-assessment-update',
  templateUrl: './fixed-asset-assessment-update.component.html'
})
export class FixedAssetAssessmentUpdateComponent implements OnInit {
  fixedAssetAssessment: IFixedAssetAssessment;
  isSaving: boolean;
  assessmentDateDp: any;
  nextAssessmentDateDp: any;

  editForm = this.fb.group({
    id: [],
    description: [],
    assetCondition: [null, [Validators.required]],
    assessmentDate: [],
    assessmentRemarks: [],
    nameOfAssessingStaff: [],
    nameOfAssessmentContractor: [],
    currentServiceOutletCode: [],
    currentPhysicalAddress: [],
    nextAssessmentDate: [],
    nameOfUser: [],
    fixedAssetPicture: [],
    fixedAssetPictureContentType: [],
    fixedAssetItemId: [],
    estimatedValue: [],
    estimatedUsefulMonths: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected fixedAssetAssessmentService: FixedAssetAssessmentService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fixedAssetAssessment }) => {
      this.updateForm(fixedAssetAssessment);
      this.fixedAssetAssessment = fixedAssetAssessment;
    });
  }

  updateForm(fixedAssetAssessment: IFixedAssetAssessment) {
    this.editForm.patchValue({
      id: fixedAssetAssessment.id,
      description: fixedAssetAssessment.description,
      assetCondition: fixedAssetAssessment.assetCondition,
      assessmentDate: fixedAssetAssessment.assessmentDate,
      assessmentRemarks: fixedAssetAssessment.assessmentRemarks,
      nameOfAssessingStaff: fixedAssetAssessment.nameOfAssessingStaff,
      nameOfAssessmentContractor: fixedAssetAssessment.nameOfAssessmentContractor,
      currentServiceOutletCode: fixedAssetAssessment.currentServiceOutletCode,
      currentPhysicalAddress: fixedAssetAssessment.currentPhysicalAddress,
      nextAssessmentDate: fixedAssetAssessment.nextAssessmentDate,
      nameOfUser: fixedAssetAssessment.nameOfUser,
      fixedAssetPicture: fixedAssetAssessment.fixedAssetPicture,
      fixedAssetPictureContentType: fixedAssetAssessment.fixedAssetPictureContentType,
      fixedAssetItemId: fixedAssetAssessment.fixedAssetItemId,
      estimatedValue: fixedAssetAssessment.estimatedValue,
      estimatedUsefulMonths: fixedAssetAssessment.estimatedUsefulMonths
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fixedAssetAssessment = this.createFromForm();
    if (fixedAssetAssessment.id !== undefined) {
      this.subscribeToSaveResponse(this.fixedAssetAssessmentService.update(fixedAssetAssessment));
    } else {
      this.subscribeToSaveResponse(this.fixedAssetAssessmentService.create(fixedAssetAssessment));
    }
  }

  private createFromForm(): IFixedAssetAssessment {
    const entity = {
      ...new FixedAssetAssessment(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      assetCondition: this.editForm.get(['assetCondition']).value,
      assessmentDate: this.editForm.get(['assessmentDate']).value,
      assessmentRemarks: this.editForm.get(['assessmentRemarks']).value,
      nameOfAssessingStaff: this.editForm.get(['nameOfAssessingStaff']).value,
      nameOfAssessmentContractor: this.editForm.get(['nameOfAssessmentContractor']).value,
      currentServiceOutletCode: this.editForm.get(['currentServiceOutletCode']).value,
      currentPhysicalAddress: this.editForm.get(['currentPhysicalAddress']).value,
      nextAssessmentDate: this.editForm.get(['nextAssessmentDate']).value,
      nameOfUser: this.editForm.get(['nameOfUser']).value,
      fixedAssetPictureContentType: this.editForm.get(['fixedAssetPictureContentType']).value,
      fixedAssetPicture: this.editForm.get(['fixedAssetPicture']).value,
      fixedAssetItemId: this.editForm.get(['fixedAssetItemId']).value,
      estimatedValue: this.editForm.get(['estimatedValue']).value,
      estimatedUsefulMonths: this.editForm.get(['estimatedUsefulMonths']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFixedAssetAssessment>>) {
    result.subscribe((res: HttpResponse<IFixedAssetAssessment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
