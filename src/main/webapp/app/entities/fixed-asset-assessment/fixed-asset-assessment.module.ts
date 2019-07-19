import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  FixedAssetAssessmentComponent,
  FixedAssetAssessmentDetailComponent,
  FixedAssetAssessmentUpdateComponent,
  FixedAssetAssessmentDeletePopupComponent,
  FixedAssetAssessmentDeleteDialogComponent,
  fixedAssetAssessmentRoute,
  fixedAssetAssessmentPopupRoute
} from './';

const ENTITY_STATES = [...fixedAssetAssessmentRoute, ...fixedAssetAssessmentPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FixedAssetAssessmentComponent,
    FixedAssetAssessmentDetailComponent,
    FixedAssetAssessmentUpdateComponent,
    FixedAssetAssessmentDeleteDialogComponent,
    FixedAssetAssessmentDeletePopupComponent
  ],
  entryComponents: [
    FixedAssetAssessmentComponent,
    FixedAssetAssessmentUpdateComponent,
    FixedAssetAssessmentDeleteDialogComponent,
    FixedAssetAssessmentDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsFixedAssetAssessmentModule {}
