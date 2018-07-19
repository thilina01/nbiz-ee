/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { BrandDetailComponent } from 'app/entities/brand/brand-detail.component';
import { Brand } from 'app/shared/model/brand.model';

describe('Component Tests', () => {
    describe('Brand Management Detail Component', () => {
        let comp: BrandDetailComponent;
        let fixture: ComponentFixture<BrandDetailComponent>;
        const route = ({ data: of({ brand: new Brand(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [BrandDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BrandDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BrandDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.brand).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
