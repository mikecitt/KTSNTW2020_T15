import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CulturalOfferType } from 'src/app/models/cultural-offer-type';
import { CulturalOfferTypeListComponent } from './cultural-offer-type-list.component';

describe('CulturalOfferTypeListComponent', () => {
  let component: CulturalOfferTypeListComponent;
  let fixture: ComponentFixture<CulturalOfferTypeListComponent>;

  const typesMock : CulturalOfferType[] = [
    {
      id: 1,
      name: 'tip1'
    },
    {
      id:2,
      name: 'tip2'
    }
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CulturalOfferTypeListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should assign type list to dataSource', async() => {
    component.offerTypes = typesMock;
    component.ngOnInit();
    expect(component.dataSource).toBeDefined();
    expect(component.dataSource.data.length).toBe(2);
    expect(component.dataSource.data).toEqual(typesMock);
  });

  it('should assign type list to dataSource on changes', async() => {
    component.offerTypes = typesMock;
    // fixture.detectChanges();
    component.ngOnChanges();
    expect(component.dataSource).toBeDefined();
    expect(component.dataSource.data.length).toBe(2);
    expect(component.dataSource.data).toEqual(typesMock);
  });

  it('should emit on click create', async () =>{
    spyOn(component.onCreateTypeEvent, 'emit');
    component.createType();
    fixture.detectChanges();
    expect(component.onCreateTypeEvent.emit).toHaveBeenCalled();
    expect(component.onCreateTypeEvent.emit).toHaveBeenCalledWith('create');
  });

  it('should emit on click delete', async () => {
    spyOn(component.onDeleteTypeEvent,'emit');
    component.deleteType({id: 1});
    fixture.detectChanges();
    expect(component.onDeleteTypeEvent.emit).toHaveBeenCalled();
    expect(component.onDeleteTypeEvent.emit).toHaveBeenCalledWith(1);
  });

  it('should emit on click updateType', async () => {
    spyOn(component.onUpdateTypeEvent,'emit');
    component.updateType({id:1, name:"tip"});
    fixture.detectChanges();
    expect(component.onUpdateTypeEvent.emit).toHaveBeenCalled();
    expect(component.onUpdateTypeEvent.emit).toHaveBeenCalledWith({id:1, name:"tip"});
  });


});
