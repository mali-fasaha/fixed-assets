import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { NGXLogger } from 'ngx-logger';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IAssetAcquisition } from 'app/shared/model/asset-acquisition.model';
import { Subscription } from 'rxjs/index';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
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
    // we were able to remove this by removing *ngIf="assetAcquisitions" from the template
    // this.loadAmortizationEntities();
  }

  ngOnInit() {
    this.dtOptions = this.getDataTableOptions();
    this.loadAmortizationEntities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  loadAmortizationEntities() {
    this.assetAcquisitionDataTableService.fetchAllAssetAcquisitions().subscribe(res => {
      this.assetAcquisitions = res.body;
      this.dtTrigger.next();
    });
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
      pageLength: 5,
      processing: true,
      dom: 'Bfrtip',
      buttons: ['copy', 'csv', 'excel', 'pdf', 'print', 'colvis']
    });
  }

  loadAll() {
    if (this.currentSearch) {
      this.assetAcquisitionDataTableService
        .fetchAllAssetAcquisitions()
        .subscribe(
          (res: HttpResponse<IAssetAcquisition[]>) => this.populateAssetAsquisitions(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.assetAcquisitionDataTableService
      .fetchAllAssetAcquisitions()
      .subscribe(
        (res: HttpResponse<IAssetAcquisition[]>) => this.populateAssetAsquisitions(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  protected populateAssetAsquisitions(data: IAssetAcquisition[], headers: HttpHeaders) {
    this.assetAcquisitions = data;
  }
}
