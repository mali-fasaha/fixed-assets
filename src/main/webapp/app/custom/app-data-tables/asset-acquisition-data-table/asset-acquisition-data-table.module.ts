import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AssetAcquisitionDataTableRoutingModule } from './asset-acquisition-data-table-routing.module';
import { FixedAssetsSharedModule } from 'app/shared';
import { DataTablesModule as DTModule } from 'angular-datatables';

@NgModule({
  declarations: [],
  imports: [CommonModule, AssetAcquisitionDataTableRoutingModule, FixedAssetsSharedModule, DTModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AssetAcquisitionDataTableModule {}
