/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AssetDepreciationService } from 'app/entities/asset-depreciation/asset-depreciation.service';
import { IAssetDepreciation, AssetDepreciation } from 'app/shared/model/asset-depreciation.model';

describe('Service Tests', () => {
  describe('AssetDepreciation Service', () => {
    let injector: TestBed;
    let service: AssetDepreciationService;
    let httpMock: HttpTestingController;
    let elemDefault: IAssetDepreciation;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AssetDepreciationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AssetDepreciation(0, 'AAAAAAA', 0, currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            depreciationDate: currentDate.format(DATE_FORMAT)
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

      it('should create a AssetDepreciation', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            depreciationDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            depreciationDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new AssetDepreciation(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AssetDepreciation', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            depreciationAmount: 1,
            depreciationDate: currentDate.format(DATE_FORMAT),
            categoryId: 1,
            assetItemId: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            depreciationDate: currentDate
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

      it('should return a list of AssetDepreciation', async () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            depreciationAmount: 1,
            depreciationDate: currentDate.format(DATE_FORMAT),
            categoryId: 1,
            assetItemId: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            depreciationDate: currentDate
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

      it('should delete a AssetDepreciation', async () => {
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
