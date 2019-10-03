import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CapitalWipDataTableRoutingModule } from './capital-wip-data-table-routing.module';
import { CapitalWipDataTableComponent } from './capital-wip-data-table.component';

@NgModule({
  declarations: [CapitalWipDataTableComponent],
  imports: [CommonModule, CapitalWipDataTableRoutingModule],
  exports: [CapitalWipDataTableComponent],
  entryComponents: [CapitalWipDataTableComponent]
})
export class CapitalWipDataTableModule {}
