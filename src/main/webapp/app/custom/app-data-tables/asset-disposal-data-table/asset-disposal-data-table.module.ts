import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AssetDisposalDataTableRoutingModule } from './asset-disposal-data-table-routing.module';
import { AssetDisposalDataTableComponent } from './asset-disposal-data-table.component';
import { FixedAssetsAssetDisposalModule } from 'app/entities/asset-disposal/asset-disposal.module';
import { DataTablesModule as DTModule } from 'angular-datatables';
import { FixedAssetsSharedModule } from 'app/shared';

@NgModule({
  declarations: [AssetDisposalDataTableComponent],
  imports: [CommonModule, FixedAssetsSharedModule, FixedAssetsAssetDisposalModule, AssetDisposalDataTableRoutingModule, DTModule],
  exports: [AssetDisposalDataTableComponent],
  entryComponents: [AssetDisposalDataTableComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AssetDisposalDataTableModule {}
