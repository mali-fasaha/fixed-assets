/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetTransactionDetailComponent } from 'app/entities/asset-transaction/asset-transaction-detail.component';
import { AssetTransaction } from 'app/shared/model/asset-transaction.model';

describe('Component Tests', () => {
  describe('AssetTransaction Management Detail Component', () => {
    let comp: AssetTransactionDetailComponent;
    let fixture: ComponentFixture<AssetTransactionDetailComponent>;
    const route = ({ data: of({ assetTransaction: new AssetTransaction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetTransactionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssetTransactionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssetTransactionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assetTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
