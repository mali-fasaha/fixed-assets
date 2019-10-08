import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CapitalWipDataTableRoutingModule } from './capital-wip-data-table-routing.module';
import { CapitalWipDataTableComponent } from './capital-wip-data-table.component';
import { FixedAssetsSharedModule } from 'app/shared';
import { DataTablesModule as DTModule } from 'angular-datatables';
import { FixedAssetsCapitalWorkInProgressModule } from 'app/entities/capital-work-in-progress/capital-work-in-progress.module';

@NgModule({
  declarations: [CapitalWipDataTableComponent],
  imports: [CommonModule, FixedAssetsSharedModule, FixedAssetsCapitalWorkInProgressModule, CapitalWipDataTableRoutingModule, DTModule],
  exports: [CapitalWipDataTableComponent],
  entryComponents: [CapitalWipDataTableComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapitalWipDataTableModule {}
