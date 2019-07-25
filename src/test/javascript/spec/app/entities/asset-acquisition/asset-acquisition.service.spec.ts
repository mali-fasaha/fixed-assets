/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AssetAcquisitionService } from 'app/entities/asset-acquisition/asset-acquisition.service';
import { IAssetAcquisition, AssetAcquisition } from 'app/shared/model/asset-acquisition.model';

describe('Service Tests', () => {
  describe('AssetAcquisition Service', () => {
    let injector: TestBed;
    let service: AssetAcquisitionService;
    let httpMock: HttpTestingController;
    let elemDefault: IAssetAcquisition;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AssetAcquisitionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AssetAcquisition(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            acquisitionMonth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a AssetAcquisition', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            acquisitionMonth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            acquisitionMonth: currentDate
          },
          returnedFromService
        );
        service
          .create(new AssetAcquisition(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AssetAcquisition', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            acquisitionMonth: currentDate.format(DATE_FORMAT),
            assetSerial: 'BBBBBB',
            serviceOutletCode: 'BBBBBB',
            acquisitionTransactionId: 1,
            assetCategoryId: 1,
            purchaseAmount: 1,
            assetDealerId: 1,
            assetInvoiceId: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            acquisitionMonth: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of AssetAcquisition', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            acquisitionMonth: currentDate.format(DATE_FORMAT),
            assetSerial: 'BBBBBB',
            serviceOutletCode: 'BBBBBB',
            acquisitionTransactionId: 1,
            assetCategoryId: 1,
            purchaseAmount: 1,
            assetDealerId: 1,
            assetInvoiceId: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            acquisitionMonth: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AssetAcquisition', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
