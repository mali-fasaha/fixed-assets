import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AssetItemDataTableRoutingModule } from './asset-item-data-table-routing.module';
import { FixedAssetsSharedModule } from 'app/shared';
import { DataTablesModule as DTModule } from 'angular-datatables';
import { AssetItemDataTableComponent } from './asset-item-data-table.component';

@NgModule({
  declarations: [AssetItemDataTableComponent],
  imports: [CommonModule, AssetItemDataTableRoutingModule, FixedAssetsSharedModule, DTModule],
  exports: [AssetItemDataTableComponent],
  entryComponents: [AssetItemDataTableComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AssetItemDataTableModule {}
