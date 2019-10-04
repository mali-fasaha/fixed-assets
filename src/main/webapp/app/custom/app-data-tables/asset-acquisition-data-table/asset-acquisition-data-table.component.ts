import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { NGXLogger } from 'ngx-logger';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IAssetAcquisition } from 'app/shared/model/asset-acquisition.model';
import { Subscription } from 'rxjs/index';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { AssetAcquisitionDataTableService } from 'app/custom/app-data-tables/asset-acquisition-data-table/asset-acquisition-data-table.service';

@Component({
  selector: 'gha-asset-acquisition-data-table',
  templateUrl: './asset-acquisition-data-table.component.html',
  styleUrls: ['./asset-acquisition-data-table.component.scss']
})
export class AssetAcquisitionDataTableComponent implements OnInit, OnDestroy {
  currentAccount: any;
  currentSearch: string;
  assetAcquisitions: IAssetAcquisition[];
  dtOptions: DataTables.Settings;
  eventSubscriber: Subscription;
  dtTrigger: Subject<any> = new Subject<any>();
  reportDate: string;

  constructor(
    private log: NGXLogger,
    private router: Router,
    protected eventManager: JhiEventManager,
    private jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected assetAcquisitionDataTableService: AssetAcquisitionDataTableService
  ) {
    this.dtOptions = this.getDataTableOptions();
  }

  ngOnInit() {}

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAssetAcquisition) {
    return item.id;
  }

  registerChangeInAssetAcquisitions() {
    this.eventSubscriber = this.eventManager.subscribe('assetAcquisitionListModification', response => this.loadAll());
  }

  private getDataTableOptions() {
    return (this.dtOptions = {
      searching: true,
      paging: true,
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true,
      dom: 'Bfrtip',
      buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis']
    });
  }

  loadAll() {
    if (this.currentSearch) {
      this.assetAcquisitionDataTableService
        .search({
          page: this.page - 1,
          query: this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IAssetAcquisition[]>) => this.paginateAssetAcquisitions(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.assetAcquisitionDataTableService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAssetAcquisition[]>) => this.paginateAssetAcquisitions(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
