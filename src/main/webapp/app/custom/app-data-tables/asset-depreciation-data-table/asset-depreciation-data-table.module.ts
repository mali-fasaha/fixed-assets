import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AssetDepreciationDataTableRoutingModule } from './asset-depreciation-data-table-routing.module';
import { AssetDepreciationDataTableComponent } from './asset-depreciation-data-table.component';

@NgModule({
  declarations: [AssetDepreciationDataTableComponent],
  imports: [CommonModule, AssetDepreciationDataTableRoutingModule],
  exports: [AssetDepreciationDataTableComponent],
  entryComponents: [AssetDepreciationDataTableComponent]
})
export class AssetDepreciationDataTableModule {}
