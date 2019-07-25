import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  CapitalWorkInProgressComponent,
  CapitalWorkInProgressDetailComponent,
  CapitalWorkInProgressUpdateComponent,
  CapitalWorkInProgressDeletePopupComponent,
  CapitalWorkInProgressDeleteDialogComponent,
  capitalWorkInProgressRoute,
  capitalWorkInProgressPopupRoute
} from './';

const ENTITY_STATES = [...capitalWorkInProgressRoute, ...capitalWorkInProgressPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CapitalWorkInProgressComponent,
    CapitalWorkInProgressDetailComponent,
    CapitalWorkInProgressUpdateComponent,
    CapitalWorkInProgressDeleteDialogComponent,
    CapitalWorkInProgressDeletePopupComponent
  ],
  entryComponents: [
    CapitalWorkInProgressComponent,
    CapitalWorkInProgressUpdateComponent,
    CapitalWorkInProgressDeleteDialogComponent,
    CapitalWorkInProgressDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsCapitalWorkInProgressModule {}
