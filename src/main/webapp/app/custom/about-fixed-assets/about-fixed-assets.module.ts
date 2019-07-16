import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';

import { AboutFixedAssetsRoutingModule } from './about-fixed-assets-routing.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AboutFixedAssetsRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AboutFixedAssetsModule { }
