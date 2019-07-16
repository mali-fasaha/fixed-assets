import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomRoutingModule } from './custom-routing.module';
import { AboutFixedAssetsModule } from './about-fixed-assets/about-fixed-assets.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CustomRoutingModule,
    AboutFixedAssetsModule
  ],
  exports: [
    AboutFixedAssetsModule
  ]
})
export class CustomModule { }
