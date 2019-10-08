import { Component, OnInit } from '@angular/core';
import { IFixedAssetItem } from 'app/shared/model/fixed-asset-item.model';
import { Subscription } from 'rxjs/index';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subject } from 'rxjs/internal/Subject';
import { NGXLogger } from 'ngx-logger';
import { AssetAcquisitionDataTableService } from 'app/custom/app-data-tables/asset-acquisition-data-table/asset-acquisition-data-table.service';
import { IAssetAcquisition } from 'app/shared/model/asset-acquisition.model';

@Component({
  selector: 'gha-asset-item-data-table',
  templateUrl: './asset-item-data-table.component.html',
  styleUrls: ['./asset-item-data-table.component.scss']
})
export class AssetItemDataTableComponent implements OnInit {
  fixedAssetItems: IFixedAssetItem[];
  currentAccount: any;
  currentSearch: string;
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
  ) {}

  ngOnInit() {
    this.dtOptions = this.getDataTableOptions();
    this.loadAmortizationEntities();
    this.registerChangeInAssetAcquisitions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  loadAmortizationEntities() {
    this.assetAcquisitionDataTableService.fetchAllAssetAcquisitions().subscribe(res => {
      this.fixedAssetItems = res.body;
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

  protected view(id: number) {
    this.router
      .navigate(['/asset-acquisition', id, 'view'])
      .then(() => {
        this.log.debug(`Successfully navigated to the path ${this.router.getCurrentNavigation()}`);
      })
      .catch(error => {
        this.onError(`An error has occurred; could not navigate to ${this.router.getCurrentNavigation()} due to ${error}`);
      });
  }

  protected edit(id: number) {
    this.router
      .navigate(['/asset-acquisition', id, 'edit'])
      .then(() => {
        this.log.debug(`Successfully navigated to the path ${this.router.getCurrentNavigation()}`);
      })
      .catch(error => {
        this.onError(`An error has occurred; could not navigate to ${this.router.getCurrentNavigation()} due to ${error}`);
      });
  }

  protected delete(id: number) {
    // TODO replaceUrl="true"
    // TODO queryParamsHandling="merge"
    // TODO Create popup query
    const viewPath = '/asset-acquisition/' + id + '/view/';
    this.router
      .navigateByUrl(viewPath)
      .then(() => {
        this.log.debug(`Successfully navigated to the path ${viewPath}`);
      })
      .catch(error => {
        this.onError(`An error has occurred; could not navigate to ${viewPath} due to ${error}`);
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
    this.fixedAssetItems = data;
  }
}
