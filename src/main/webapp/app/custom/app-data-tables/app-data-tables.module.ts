import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppDataTablesRoutingModule } from './app-data-tables-routing.module';
import { AssetItemDataTableModule } from './asset-item-data-table/asset-item-data-table.module';
import { AssetAcquisitionDataTableModule } from './asset-acquisition-data-table/asset-acquisition-data-table.module';
import { AssetDepreciationDataTableModule } from './asset-depreciation-data-table/asset-depreciation-data-table.module';
import { AssetDisposalDataTableModule } from './asset-disposal-data-table/asset-disposal-data-table.module';
import { CapitalWipDataTableModule } from './capital-wip-data-table/capital-wip-data-table.module';

/**
 * This module exists to portray data components in the form of web tables that can be exported
 * into pdf and excel directly from the from the front-end
 */
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AppDataTablesRoutingModule,
    AssetItemDataTableModule,
    AssetAcquisitionDataTableModule,
    AssetDepreciationDataTableModule,
    AssetDisposalDataTableModule,
    CapitalWipDataTableModule
  ]
})
export class AppDataTablesModule {}
