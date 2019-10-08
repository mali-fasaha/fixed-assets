import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CwipTransferDataTableRoutingModule } from './cwip-transfer-data-table-routing.module';
import { CwipTransferDataTableComponent } from './cwip-transfer-data-table.component';
import { FixedAssetsSharedModule } from 'app/shared';
import { FixedAssetsCwipTransferModule } from 'app/entities/cwip-transfer/cwip-transfer.module';
import { DataTablesModule as DTModule } from 'angular-datatables';

@NgModule({
  declarations: [CwipTransferDataTableComponent],
  imports: [CommonModule, DTModule, FixedAssetsSharedModule, FixedAssetsCwipTransferModule, CwipTransferDataTableRoutingModule],
  exports: [CwipTransferDataTableComponent],
  entryComponents: [CwipTransferDataTableComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CwipTransferDataTableModule {}
