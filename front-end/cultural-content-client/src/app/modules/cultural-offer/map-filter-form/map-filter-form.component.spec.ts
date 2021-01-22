import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup } from '@angular/forms';
import { MapFilterFormComponent } from './map-filter-form.component';

describe('MapFilterFormComponent', () => {
  let component: MapFilterFormComponent;
  let fixture: ComponentFixture<MapFilterFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MapFilterFormComponent ],
     })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapFilterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit getSubTypesEvent on onSelectChange',async () =>{
    spyOn(component.getSubTypesEvent, 'emit');
    component.onSelectChange({value: 'value'});
    fixture.detectChanges();
    expect(component.getSubTypesEvent.emit).toHaveBeenCalled();
    expect(component.disabled).toBeFalse();
  });

  it('should reset filter', async ()=>{
    spyOn(component.resetFilterEvent, 'emit');
    component.resetFilter();

    expect(component.filterForm.value).toEqual({type: '', subType: '', searchLocation: ''});
    expect(component.disabled).toBeTruthy();
    expect(component.resetFilterEvent.emit).toHaveBeenCalled();
    expect(component.resetFilterEvent.emit).toHaveBeenCalledWith("RESET");
  });

  it('should emit filter event with filterForm data on onSubmit', async ()=>{
    spyOn(component.applyFilterEvent, 'emit');
    let formObj = {
      type: {
        name: 'tip1'
      },
      subType: {
        name: 'podtip1'
      },
      searchLocation: 'lokacija'
    }
    let emitObj = {
      request: {
        subTypeName: formObj.subType.name,
        typeName: formObj.type.name
      },
      location: formObj.searchLocation
    }
    component.filterForm.setValue(formObj);

    component.onSubmit();
    expect(component.applyFilterEvent.emit).toHaveBeenCalled();
    expect(component.applyFilterEvent.emit).toHaveBeenCalledWith(emitObj);

  });

});
