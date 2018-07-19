/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { SaleInvoiceItemDetailComponent } from 'app/entities/sale-invoice-item/sale-invoice-item-detail.component';
import { SaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';

describe('Component Tests', () => {
    describe('SaleInvoiceItem Management Detail Component', () => {
        let comp: SaleInvoiceItemDetailComponent;
        let fixture: ComponentFixture<SaleInvoiceItemDetailComponent>;
        const route = ({ data: of({ saleInvoiceItem: new SaleInvoiceItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SaleInvoiceItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SaleInvoiceItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaleInvoiceItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.saleInvoiceItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
