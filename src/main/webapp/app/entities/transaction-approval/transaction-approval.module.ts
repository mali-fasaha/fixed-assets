import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  TransactionApprovalComponent,
  TransactionApprovalDetailComponent,
  TransactionApprovalUpdateComponent,
  TransactionApprovalDeletePopupComponent,
  TransactionApprovalDeleteDialogComponent,
  transactionApprovalRoute,
  transactionApprovalPopupRoute
} from './';

const ENTITY_STATES = [...transactionApprovalRoute, ...transactionApprovalPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionApprovalComponent,
    TransactionApprovalDetailComponent,
    TransactionApprovalUpdateComponent,
    TransactionApprovalDeleteDialogComponent,
    TransactionApprovalDeletePopupComponent
  ],
  entryComponents: [
    TransactionApprovalComponent,
    TransactionApprovalUpdateComponent,
    TransactionApprovalDeleteDialogComponent,
    TransactionApprovalDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsTransactionApprovalModule {}
