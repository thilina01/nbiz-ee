/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { QuotationDetailComponent } from 'app/entities/quotation/quotation-detail.component';
import { Quotation } from 'app/shared/model/quotation.model';

describe('Component Tests', () => {
    describe('Quotation Management Detail Component', () => {
        let comp: QuotationDetailComponent;
        let fixture: ComponentFixture<QuotationDetailComponent>;
        const route = ({ data: of({ quotation: new Quotation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [QuotationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuotationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuotationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.quotation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
