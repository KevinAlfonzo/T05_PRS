import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BulkRoutingModule } from './bulk-routing.module';
import { MatPaginatorModule } from '@angular/material/paginator';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    BulkRoutingModule,
    MatPaginatorModule
  ]
})
export class BulkModule { }
