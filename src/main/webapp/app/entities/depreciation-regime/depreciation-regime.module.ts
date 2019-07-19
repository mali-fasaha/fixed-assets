import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  DepreciationRegimeComponent,
  DepreciationRegimeDetailComponent,
  DepreciationRegimeUpdateComponent,
  DepreciationRegimeDeletePopupComponent,
  DepreciationRegimeDeleteDialogComponent,
  depreciationRegimeRoute,
  depreciationRegimePopupRoute
} from './';

const ENTITY_STATES = [...depreciationRegimeRoute, ...depreciationRegimePopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DepreciationRegimeComponent,
    DepreciationRegimeDetailComponent,
    DepreciationRegimeUpdateComponent,
    DepreciationRegimeDeleteDialogComponent,
    DepreciationRegimeDeletePopupComponent
  ],
  entryComponents: [
    DepreciationRegimeComponent,
    DepreciationRegimeUpdateComponent,
    DepreciationRegimeDeleteDialogComponent,
    DepreciationRegimeDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsDepreciationRegimeModule {}
