import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';

import { AboutFixedAssetsRoutingModule } from './about-fixed-assets-routing.module';
import {AboutComponent} from 'app/custom/about-fixed-assets/about.component';

@NgModule({
  declarations: [AboutComponent],
  imports: [
    CommonModule,
    AboutFixedAssetsRoutingModule
  ],
  exports: [AboutComponent, AboutFixedAssetsRoutingModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AboutFixedAssetsModule { }
