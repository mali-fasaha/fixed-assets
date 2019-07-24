/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetDepreciationDetailComponent } from 'app/entities/asset-depreciation/asset-depreciation-detail.component';
import { AssetDepreciation } from 'app/shared/model/asset-depreciation.model';

describe('Component Tests', () => {
  describe('AssetDepreciation Management Detail Component', () => {
    let comp: AssetDepreciationDetailComponent;
    let fixture: ComponentFixture<AssetDepreciationDetailComponent>;
    const route = ({ data: of({ assetDepreciation: new AssetDepreciation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetDepreciationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssetDepreciationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssetDepreciationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assetDepreciation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
