import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AssetDisposalDataTableRoutingModule } from './asset-disposal-data-table-routing.module';
import { AssetDisposalDataTableComponent } from './asset-disposal-data-table.component';

@NgModule({
  declarations: [AssetDisposalDataTableComponent],
  imports: [CommonModule, AssetDisposalDataTableRoutingModule],
  exports: [AssetDisposalDataTableComponent],
  entryComponents: [AssetDisposalDataTableComponent]
})
export class AssetDisposalDataTableModule {}
