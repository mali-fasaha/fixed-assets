import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppDataTablesRoutingModule } from './app-data-tables-routing.module';
import { AssetItemDataTableModule } from './asset-item-data-table/asset-item-data-table.module';

/**
 * This module exists to portray data components in the form of web tables that can be exported
 * into pdf and excel directly from the from the front-end
 */
@NgModule({
  declarations: [],
  imports: [CommonModule, AppDataTablesRoutingModule, AssetItemDataTableModule]
})
export class AppDataTablesModule {}
