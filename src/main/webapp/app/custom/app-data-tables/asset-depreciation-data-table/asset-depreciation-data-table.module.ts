import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AssetDepreciationDataTableRoutingModule } from './asset-depreciation-data-table-routing.module';
import { AssetDepreciationDataTableComponent } from './asset-depreciation-data-table.component';
import { FixedAssetsAssetDepreciationModule } from 'app/entities/asset-depreciation/asset-depreciation.module';
import { DataTablesModule as DTModule } from 'angular-datatables';
import { FixedAssetsSharedModule } from 'app/shared';

@NgModule({
  declarations: [AssetDepreciationDataTableComponent],
  imports: [CommonModule, FixedAssetsSharedModule, AssetDepreciationDataTableRoutingModule, FixedAssetsAssetDepreciationModule, DTModule],
  exports: [AssetDepreciationDataTableComponent],
  entryComponents: [AssetDepreciationDataTableComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AssetDepreciationDataTableModule {}
