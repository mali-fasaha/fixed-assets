import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'asset-transaction',
        loadChildren: './asset-transaction/asset-transaction.module#FixedAssetsAssetTransactionModule'
      },
      {
        path: 'scanned-document',
        loadChildren: './scanned-document/scanned-document.module#FixedAssetsScannedDocumentModule'
      },
      {
        path: 'fixed-asset-invoice',
        loadChildren: './fixed-asset-invoice/fixed-asset-invoice.module#FixedAssetsFixedAssetInvoiceModule'
      },
      {
        path: 'fixed-asset-invoice',
        loadChildren: './fixed-asset-invoice/fixed-asset-invoice.module#FixedAssetsFixedAssetInvoiceModule'
      },
      {
        path: 'dealer',
        loadChildren: './dealer/dealer.module#FixedAssetsDealerModule'
      },
      {
        path: 'fixed-asset-invoice',
        loadChildren: './fixed-asset-invoice/fixed-asset-invoice.module#FixedAssetsFixedAssetInvoiceModule'
      },
      {
        path: 'fixed-asset-item',
        loadChildren: './fixed-asset-item/fixed-asset-item.module#FixedAssetsFixedAssetItemModule'
      },
      {
        path: 'service-outlet',
        loadChildren: './service-outlet/service-outlet.module#FixedAssetsServiceOutletModule'
      },
      {
        path: 'fixed-asset-category',
        loadChildren: './fixed-asset-category/fixed-asset-category.module#FixedAssetsFixedAssetCategoryModule'
      },
      {
        path: 'depreciation-regime',
        loadChildren: './depreciation-regime/depreciation-regime.module#FixedAssetsDepreciationRegimeModule'
      },
      {
        path: 'fixed-asset-assessment',
        loadChildren: './fixed-asset-assessment/fixed-asset-assessment.module#FixedAssetsFixedAssetAssessmentModule'
      },
      {
        path: 'fixed-asset-category',
        loadChildren: './fixed-asset-category/fixed-asset-category.module#FixedAssetsFixedAssetCategoryModule'
      },
      {
        path: 'fixed-asset-item',
        loadChildren: './fixed-asset-item/fixed-asset-item.module#FixedAssetsFixedAssetItemModule'
      },
      {
        path: 'asset-depreciation',
        loadChildren: './asset-depreciation/asset-depreciation.module#FixedAssetsAssetDepreciationModule'
      },
      {
        path: 'dealer',
        loadChildren: './dealer/dealer.module#FixedAssetsDealerModule'
      },
      {
        path: 'asset-disposal',
        loadChildren: './asset-disposal/asset-disposal.module#FixedAssetsAssetDisposalModule'
      },
      {
        path: 'asset-disposal',
        loadChildren: './asset-disposal/asset-disposal.module#FixedAssetsAssetDisposalModule'
      },
      {
        path: 'asset-disposal',
        loadChildren: './asset-disposal/asset-disposal.module#FixedAssetsAssetDisposalModule'
      },
      {
        path: 'asset-acquisition',
        loadChildren: './asset-acquisition/asset-acquisition.module#FixedAssetsAssetAcquisitionModule'
      },
      {
        path: 'capital-work-in-progress',
        loadChildren: './capital-work-in-progress/capital-work-in-progress.module#FixedAssetsCapitalWorkInProgressModule'
      },
      {
        path: 'cwip-transfer',
        loadChildren: './cwip-transfer/cwip-transfer.module#FixedAssetsCwipTransferModule'
      },
      {
        path: 'employee',
        loadChildren: './employee/employee.module#FixedAssetsEmployeeModule'
      },
      {
        path: 'transaction-approval',
        loadChildren: './transaction-approval/transaction-approval.module#FixedAssetsTransactionApprovalModule'
      },
      {
        path: 'asset-transaction',
        loadChildren: './asset-transaction/asset-transaction.module#FixedAssetsAssetTransactionModule'
      },
      {
        path: 'message-token',
        loadChildren: './message-token/message-token.module#FixedAssetsMessageTokenModule'
      },
      {
        path: 'file-type',
        loadChildren: './file-type/file-type.module#FixedAssetsFileTypeModule'
      },
      {
        path: 'file-upload',
        loadChildren: './file-upload/file-upload.module#FixedAssetsFileUploadModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsEntityModule {}
