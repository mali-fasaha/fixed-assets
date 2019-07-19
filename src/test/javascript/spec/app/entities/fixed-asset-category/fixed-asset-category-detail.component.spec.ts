/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetCategoryDetailComponent } from 'app/entities/fixed-asset-category/fixed-asset-category-detail.component';
import { FixedAssetCategory } from 'app/shared/model/fixed-asset-category.model';

describe('Component Tests', () => {
  describe('FixedAssetCategory Management Detail Component', () => {
    let comp: FixedAssetCategoryDetailComponent;
    let fixture: ComponentFixture<FixedAssetCategoryDetailComponent>;
    const route = ({ data: of({ fixedAssetCategory: new FixedAssetCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FixedAssetCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FixedAssetCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fixedAssetCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
