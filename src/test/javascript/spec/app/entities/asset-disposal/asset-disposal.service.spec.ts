/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AssetDisposalService } from 'app/entities/asset-disposal/asset-disposal.service';
import { IAssetDisposal, AssetDisposal } from 'app/shared/model/asset-disposal.model';

describe('Service Tests', () => {
  describe('AssetDisposal Service', () => {
    let injector: TestBed;
    let service: AssetDisposalService;
    let httpMock: HttpTestingController;
    let elemDefault: IAssetDisposal;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AssetDisposalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AssetDisposal(0, 'AAAAAAA', currentDate, 0, 0, 0, 0, 0, 0, 0, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            disposalDate: currentDate.format(DATE_FORMAT)
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

      it('should create a AssetDisposal', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            disposalDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            disposalDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new AssetDisposal(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AssetDisposal', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            disposalDate: currentDate.format(DATE_FORMAT),
            assetCategoryId: 1,
            assetItemId: 1,
            disposalProceeds: 1,
            netBookValue: 1,
            profitOnDisposal: 1,
            scannedDocumentId: 1,
            assetDealerId: 1,
            assetPicture: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            disposalDate: currentDate
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

      it('should return a list of AssetDisposal', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            disposalDate: currentDate.format(DATE_FORMAT),
            assetCategoryId: 1,
            assetItemId: 1,
            disposalProceeds: 1,
            netBookValue: 1,
            profitOnDisposal: 1,
            scannedDocumentId: 1,
            assetDealerId: 1,
            assetPicture: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            disposalDate: currentDate
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

      it('should delete a AssetDisposal', async () => {
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
