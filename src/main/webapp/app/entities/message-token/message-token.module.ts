import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  MessageTokenComponent,
  MessageTokenDetailComponent,
  MessageTokenUpdateComponent,
  MessageTokenDeletePopupComponent,
  MessageTokenDeleteDialogComponent,
  messageTokenRoute,
  messageTokenPopupRoute
} from './';

const ENTITY_STATES = [...messageTokenRoute, ...messageTokenPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MessageTokenComponent,
    MessageTokenDetailComponent,
    MessageTokenUpdateComponent,
    MessageTokenDeleteDialogComponent,
    MessageTokenDeletePopupComponent
  ],
  entryComponents: [
    MessageTokenComponent,
    MessageTokenUpdateComponent,
    MessageTokenDeleteDialogComponent,
    MessageTokenDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsMessageTokenModule {}
