/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { QuotationItemDetailComponent } from 'app/entities/quotation-item/quotation-item-detail.component';
import { QuotationItem } from 'app/shared/model/quotation-item.model';

describe('Component Tests', () => {
    describe('QuotationItem Management Detail Component', () => {
        let comp: QuotationItemDetailComponent;
        let fixture: ComponentFixture<QuotationItemDetailComponent>;
        const route = ({ data: of({ quotationItem: new QuotationItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [QuotationItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuotationItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuotationItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.quotationItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
