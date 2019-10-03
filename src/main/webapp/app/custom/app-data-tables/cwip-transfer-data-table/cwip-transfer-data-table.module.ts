import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CwipTransferDataTableRoutingModule } from './cwip-transfer-data-table-routing.module';
import { CwipTransferDataTableComponent } from './cwip-transfer-data-table.component';

@NgModule({
  declarations: [CwipTransferDataTableComponent],
  imports: [CommonModule, CwipTransferDataTableRoutingModule],
  exports: [CwipTransferDataTableComponent],
  entryComponents: [CwipTransferDataTableComponent]
})
export class CwipTransferDataTableModule {}
